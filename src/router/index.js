import Vue from 'vue'
import Router from 'vue-router'

// 懒加载页面组件
const Home = () => import('@/views/Home.vue')
const About = () => import('@/views/About.vue')
const User = () => import('@/views/User.vue')
const NotFound = () => import('@/views/NotFound.vue')

Vue.use(Router)

export default new Router({
    mode: 'history', // 使用 history 模式去除 URL 中的 #
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
            meta: {
                title: '首页'
            }
        },
        {
            path: '/about',
            name: 'about',
            component: About,
            meta: {
                title: '天气查询',
                meta: { keepAlive: true } // 标记需要缓存的页面
            }
        },
        {
            path: '/user/:id',
            name: 'user',
            component: User,
            props: true, // 将路由参数作为 props 传递
            meta: {
                title: '用户中心'
            }
        },
        {
            path: '*',
            name: 'not-found',
            component: NotFound,
            meta: {
                title: '404 页面不存在'
            }
        }
    ],
    scrollBehavior(to, from, savedPosition) {
        // 路由切换时滚动到顶部
        if (savedPosition) {
            return savedPosition
        } else {
            return { x: 0, y: 0 }
        }
    }
})
