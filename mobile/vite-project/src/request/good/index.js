import axios from "@/request/axios";

export function getCategory(){
    return axios.get("/categories")
}