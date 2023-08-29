<template>
<div class="login_box">
    <div class="imgbox"><img src="@/assets/images/login.png" alt=""></div>
<van-row justify="center" >
    <van-form @submit="onSubmit">
            <van-cell-group inset>
                <van-field
                v-model="username"
                name="username"
                label="用户名"
                placeholder="用户名"
                :rules="[{ required: true, message: '请填写用户名' }]"
                />
                <van-field
                v-model="password"
                type="password"
                name="password"
                label="密码"
                placeholder="密码"
                :rules="[{ required: true, message: '请填写密码' }]"
                />
            </van-cell-group>
            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                提交
                </van-button>
            </div>
        </van-form>
</van-row>
       

    </div>
</template>

<script setup>
import { ref } from 'vue';
import { showLoadingToast,showSuccessToast } from 'vant';

// 登录请求函数
import {login} from "@/request/user"
// 密码加密
import {sha256} from 'js-sha256';
import {setLocal} from "@/common/js/utils" 



const username = ref('');
const password = ref('');

// 登录提交
const onSubmit = async (values) => {
    showLoadingToast({
        message: '加载中...',
        forbidClick: true,
        loadingType: 'spinner',
    });

    const {data} = await login({
        "loginName": values.username,
        "passwordSha256": sha256(values.password)
    })
    
    showSuccessToast("登陆成功，正在跳转中...")
    setLocal('token', data)
    // 需要刷新页面，否则 axios.js 文件里的 token 不会被重置
    window.location.href = '/'

 };



</script>

<style lang="less">
    .login_box{
        display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		background: #fff;
        height: 100vh;
        .imgbox{
            margin-bottom: 10px;
            img{
                width: 150px;
            }
        }
    }
</style>