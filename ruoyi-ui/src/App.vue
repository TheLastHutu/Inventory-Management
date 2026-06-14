<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <ai-assistant v-if="showAiAssistant" />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker"
import AiAssistant from "@/ai/components/AiAssistant"

export default {
  name: "App",
  components: { ThemePicker, AiAssistant },
  computed: {
    showAiAssistant() {
      return !!this.$store.getters.token && !['/login', '/register'].includes(this.$route.path)
    }
  }
}
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
