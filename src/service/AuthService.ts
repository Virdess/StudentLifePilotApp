import { get, set, remove } from "@/helper/storage";
import $axios from "../helper/axios";
import $config from "@/config";


interface LoginUserModel {
    userName: string;
    password: string;
}

interface RegisterUserModel {
    userName: string;
    phone: string;
    email: string;
    password: string;
    group:string;
}

interface ResetUserPasswordModel {
    email: string;
    password: string;
    password_confirmation: string;
    token: string;
}

interface ForgotPasswordModel {
    email: string;
}

interface TokenModel {
    token: string;
}

export async function isLoggedIn() {
    const access_token = await get("access_token")
    if (access_token) {
        return true;
    } else {
        return false;
    }
}

export async function login(user: LoginUserModel) {
    const data = await $axios({ url: $config.API_BASE_URL + 'auth/login', data: user, method: 'POST' })
        .then(resp => {
            console.log(resp)
            const access_token = resp.data.token;
            console.log(access_token)

            set("access_token", access_token)

            return true;
        })
        .catch(err => {
            console.log(err);
            remove("access_token")
            return false;
        })

        return data;
}

export async function register(user: RegisterUserModel) {
    console.log(user.email)
    const data = await $axios({url: $config.API_BASE_URL + 'auth/reg', data: user, method: 'POST' })
    .then(() => {
        return true;
    })
    .catch(err => {
      console.log(err);
      return false;
    })

    return data;
}

export async function logout(forceLogout = false) {
    if (!forceLogout) {
        await $axios({url: $config.API_BASE_URL + 'logout', method: 'POST' }).then(() => {
            remove("access_token")
            remove("is_setup")
            delete $axios.defaults.headers.common['Authorization']
        })
    } else {
        remove("access_token")
        remove("is_setup")
        delete $axios.defaults.headers.common['Authorization']
    }
}

export async function refreshToken() {
    const data = await $axios({url: $config.API_BASE_URL + 'refresh', method: 'POST' })
    .then(resp => {
      const access_token = resp.data.access_token
      set("access_token", access_token)

      return resp.data.access_token;
    })
    .catch((err) => {
      console.log(err);
      return false;
    })

    return data;
}

export async function forgotPassword(email: ForgotPasswordModel) {
    const data = await $axios({url: $config.API_BASE_URL + 'forgotPassword', data: email, method: 'POST' })
    .then(response => {
        if (response.data.status) {
            return true;
        } 
        
        return false;
    })
    .catch(err => {
        console.log(err);
        return false;
    })

    return data;
}


export async function resetPassword(credentials: ResetUserPasswordModel) {

    const data = await $axios({url: $config.API_BASE_URL + 'resetPassword', data: credentials, method: 'POST' })
    .then(response => {
        if (response.data.status) {
            return true;
        } 
    
        return false;
    })
    .catch(err => {
        console.log(err);
        return false;
    })

    return data;
}

export async function storeFCMToken(token: TokenModel) {
    const data = await $axios({url: $config.API_BASE_URL + 'fcmtoken', data: token, method: 'POST' })
    .then(() => {
        return true;
    })
    .catch(err => {
      console.log(err);
      return false;
    })

    return data;
}