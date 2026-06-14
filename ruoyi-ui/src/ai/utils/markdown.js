function escapeHtml(text) {
  return String(text || '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')
}

function renderInline(text) {
  return escapeHtml(text)
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
    .replace(/\[([^\]]+)\]\((https?:\/\/[^)]+)\)/g, '<a href="$2" target="_blank" rel="noopener noreferrer">$1</a>')
}

function renderTable(lines, start) {
  const header = lines[start].split('|').map(item => item.trim()).filter(Boolean)
  const rows = []
  let index = start + 2
  while (index < lines.length && /^\s*\|/.test(lines[index])) {
    rows.push(lines[index].split('|').map(item => item.trim()).filter(Boolean))
    index++
  }
  const normalizedHeader = header.join(',')
  const tableClass = /指标|数值|项目|数量|金额|占比/.test(normalizedHeader) && header.length <= 4
    ? ' ai-md-metric-table'
    : ''
  let html = '<div class="ai-md-table-wrap' + tableClass + '"><table><thead><tr>'
  html += header.map(item => '<th>' + renderInline(item) + '</th>').join('')
  html += '</tr></thead><tbody>'
  html += rows.map(row => '<tr>' + row.map(item => '<td>' + renderInline(item) + '</td>').join('') + '</tr>').join('')
  html += '</tbody></table></div>'
  return { html, next: index }
}

function renderBlockquote(lines, start) {
  const quoteLines = []
  let index = start
  while (index < lines.length && /^\s*>\s?/.test(lines[index])) {
    quoteLines.push(lines[index].replace(/^\s*>\s?/, ''))
    index++
  }
  return {
    html: '<div class="ai-md-callout">' + quoteLines.map(item => '<p>' + renderInline(item) + '</p>').join('') + '</div>',
    next: index
  }
}

export function renderMarkdown(markdown) {
  const lines = String(markdown || '').split(/\r?\n/)
  const html = []
  let index = 0
  let inList = false
  let inCode = false
  let codeLines = []

  const closeList = () => {
    if (inList) {
      html.push('</ul>')
      inList = false
    }
  }

  while (index < lines.length) {
    const line = lines[index]

    if (/^```/.test(line)) {
      if (inCode) {
        html.push('<pre><code>' + escapeHtml(codeLines.join('\n')) + '</code></pre>')
        codeLines = []
        inCode = false
      } else {
        closeList()
        inCode = true
      }
      index++
      continue
    }

    if (inCode) {
      codeLines.push(line)
      index++
      continue
    }

    if (/^\s*\|(.+)\|\s*$/.test(line) && index + 1 < lines.length && /^\s*\|?\s*:?-{3,}:?\s*\|/.test(lines[index + 1])) {
      closeList()
      const table = renderTable(lines, index)
      html.push(table.html)
      index = table.next
      continue
    }

    if (/^\s*>\s?/.test(line)) {
      closeList()
      const blockquote = renderBlockquote(lines, index)
      html.push(blockquote.html)
      index = blockquote.next
      continue
    }

    if (/^\s*[-*]\s+/.test(line)) {
      if (!inList) {
        html.push('<ul>')
        inList = true
      }
      html.push('<li>' + renderInline(line.replace(/^\s*[-*]\s+/, '')) + '</li>')
      index++
      continue
    }

    closeList()

    const headingMatch = line.match(/^(#{1,6})\s+(.+)$/)
    if (!line.trim()) {
      html.push('')
    } else if (headingMatch) {
      const level = headingMatch[1].length
      html.push('<h' + level + '>' + renderInline(headingMatch[2]) + '</h' + level + '>')
    } else {
      html.push('<p>' + renderInline(line) + '</p>')
    }
    index++
  }

  closeList()
  if (inCode) {
    html.push('<pre><code>' + escapeHtml(codeLines.join('\n')) + '</code></pre>')
  }
  return html.join('\n')
}
