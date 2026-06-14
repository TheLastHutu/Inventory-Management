<template>
  <div class="login-container">
    <!-- 登录卡片 -->
    <div class="login-card">
      <div class="login-left">
        <div class="left-content">
          <h2>智发•进销存管理系统</h2>
          <div class="feature-list">
            <div class="feature-item">
              <i class="el-icon-shopping-bag-1"></i>
              <span class="feature-title">采购管理</span>
              <span class="feature-desc">采购账单分析，商品分类管理</span>
            </div>
            <div class="feature-item">
              <i class="el-icon-box"></i>
              <span class="feature-title">库存管理</span>
              <span class="feature-desc">实时库存跟踪，仓库统计管理</span>
            </div>
            <div class="feature-item">
              <i class="el-icon-shopping-cart-full"></i>
              <span class="feature-title">销售管理</span>
              <span class="feature-desc">客户信息管理，销售数据统计</span>
            </div>
            <div class="feature-item">
              <i class="el-icon-data-analysis"></i>
              <span class="feature-title">账单统计</span>
              <span class="feature-desc">财务账单，金额统计，决策分析</span>
            </div>
          </div>
        </div>
      </div>

      <div class="login-right">
        <div class="login-form-box">
          <h3 class="form-title">账户登录</h3>

          <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                type="text"
                placeholder="请输入账号"
                prefix-icon="el-icon-user"
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                @keyup.enter.native="handleLogin"
              />
            </el-form-item>

            <el-form-item prop="code" v-if="captchaEnabled">
              <div class="code-row">
                <el-input
                  v-model="loginForm.code"
                  placeholder="验证码"
                  prefix-icon="el-icon-picture"
                />
                <img :src="codeUrl" @click="getCode" class="code-img"  alt=""/>
              </div>
            </el-form-item>

            <div class="remember-row">
              <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
            </div>

            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                class="login-btn"
                @click.native.prevent="handleLogin"
              >
                {{ loading ? "登录中..." : "登 录" }}
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [{ required: true, trigger: "blur", message: "请输入账号" }],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled ?? true
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username || this.loginForm.username,
        password: password ? decrypt(password) : this.loginForm.password,
        rememberMe: rememberMe === 'true'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (!valid) return
        this.loading = true

        if (this.loginForm.rememberMe) {
          Cookies.set("username", this.loginForm.username, { expires: 30 })
          Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
          Cookies.set('rememberMe', true, { expires: 30 })
        } else {
          Cookies.remove("username")
          Cookies.remove("password")
          Cookies.remove('rememberMe')
        }

        this.$store.dispatch("Login", this.loginForm).then(() => {
          this.$router.push({ path: "/" })
        }).catch(() => {
          this.loading = false
          this.getCode()
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1c8e8e 0%, #4166ea 100%);
  position: relative;
  overflow: hidden;
}

/* 科技感背景装饰点点 */
.login-container::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: radial-gradient(rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 30px 30px;
  opacity: 0.3;
}

.login-card {
  /* 放大核心尺寸 */
  width: 1200px;
  height: 650px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 10px 60px rgba(0, 0, 0, 0.2);
  display: flex;
  overflow: hidden;
  position: relative;
  z-index: 10;
}

/* 左侧彩色区域 */
.login-left {
  width: 55%;
  background: linear-gradient(135deg, #41d097 0%, #4166ea 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;

  .left-content {
    text-align: center;

    h2 {
      font-size: 56px;
      margin-bottom: 24px;
      font-weight: 600;
    }

    p {
      font-size: 14px;
      opacity: 0.8;
    }

    .feature-list {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 20px;
      margin-top: 60px;
    }

    .feature-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 6px;
      padding: 16px 12px;
      background: rgba(255, 255, 255, 0.15);
      border-radius: 12px;
      backdrop-filter: blur(10px);
      transition: all 0.3s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.25);
        transform: translateY(-4px);
      }

      i {
        font-size: 28px;
        opacity: 0.9;
      }

      .feature-title {
        font-size: 14px;
        font-weight: 500;
        opacity: 0.95;
      }

      .feature-desc {
        font-size: 11px;
        opacity: 0.75;
        text-align: center;
        line-height: 1.4;
      }
    }
  }
}

/* 右侧表单区域 */
.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 30px 50px; /* 增大内边距 */
}

.login-form-box {
  width: 100%;
  max-width: 340px; /* 加宽表单 */

  .form-title {
    font-size: 24px;
    font-weight: 500;
    color: #333;
    margin-bottom: 30px;
    text-align: center;
  }
}

.code-row {
  display: flex;
  align-items: center;
  gap: 12px;

  .el-input {
    flex: 1;
  }

  .code-img {
    width: 120px;
    height: 44px;
    border-radius: 4px;
    cursor: pointer;
    object-fit: cover;
  }
}

.remember-row {
  margin: -10px 0 20px 0;
  font-size: 14px;
}

.login-btn {
  width: 100%;
  height: 48px; /* 加高按钮 */
  border-radius: 6px;
  font-size: 18px; /* 加大文字 */
  background: linear-gradient(135deg, #26e1e6 0%, #4166ea 100%);
  border: none;
}


</style>
