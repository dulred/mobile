import axios from "@/request/axios";

/**
 * @description 登录请求
 * @author dulred
 * @date 27/08/2023  
 */
export function login(params){
    return axios.post('/user/login',params)
}

export function getUserInfo (){
    return axios.get("/user/info")
}