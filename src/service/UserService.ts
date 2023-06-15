import { get, set, remove } from "../helper/storage";
import $axios from "../helper/axios";
import $config from "@/config";


interface UserDetailDataModel {
    activity: number;
    birthday: string;
    created_at: string;
    gender: number;
    goal: number;
    height: number;
    id: number;
    language: string;
    settings: string;
    updated_at: string;
    user_id: number;
    weight: number;
}

interface ChangeQuery{
    day:string;
    month:string;
}

interface TimetableToday{

}

interface TimetableTomorrow{

}

interface GetUserLessons{
    
}

interface GetGroups{
    _id:string;
    groupName:string;
}

interface UserDataModel {
    createdAt:TimeRanges;
    email:string;
    phone:string;
    role: Array<string>;
    status:string;
    updatedAt:TimeRanges;
    userName: string;
    group:string;
    _id: string;

}

interface UpdateUserDataModel {
    gender: number;
    dob: string;
    weight: number;
    height: number;
    activity: number;
    goal: number;
    language: string;
}

interface UpdateSettingModel {
    key: string;
    value: string;
}

interface WaterConsumptionModel {
    ml: number;
}

interface TrackWeightModel {
    weight: number;
}

export async function isSetup() {
    const set_up = await get("is_setup")
    if (set_up == 'true') {
        return true;
    } else {
        return false;
    }
}

export async function updateUserData(detail: UpdateUserDataModel) {
    const data = await $axios({ url: $config.API_BASE_URL + 'user', data: detail, method: 'POST' })
        .then(resp => {
            if (resp.data.user.calories > 0) {
                const is_setup = 'true';
                set("is_setup", is_setup)
                return true;
            } else {
                return false;
            }
        })
        .catch(err => {
            console.log(err);
            return false;
        })

    return data;
}

export async function getUserData() {
    const access_token = await get("access_token")
    const data = await $axios({url:$config.API_BASE_URL + 'auth/me', headers:{ 'Authorization': `Bearer ${access_token}`}})
        .then(resp => {
            console.log(resp.data)
            const username = resp.data.userName;
            const role = resp.data.role[0];
            set("username", username)
            set("role", role)
            
            return resp.data as UserDataModel;
        })
        .catch(err => {
            console.log(err);
            return null;
        })

    return data;
}

export async function getAllGroups() {
    const data = await $axios($config.API_BASE_URL + 'groups')
        .then(resp => {
            console.log(resp.data)
            
            return resp.data as GetGroups;
        })
        .catch(err => {
            console.log(err);
            return null;
        })

    return data;
}

export async function updateUserSetting(setting: UpdateSettingModel) {
    const data = await $axios({ url: $config.API_BASE_URL + 'updateSettings', data: setting, method: 'POST' })
        .then(resp => {
            if (resp.data.status) {
                return true;
            } else {
                return false;
            }
        })
        .catch(err => {
            console.log(err);
            return false;
        })

    return data;
}

export async function trackWeight(weight: TrackWeightModel) {
    const data = await $axios({ url: $config.API_BASE_URL + 'trackWeight', data: weight, method: 'POST' })
        .then(response => {
            if (response.data.weight) {
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

export async function trackWaterConsumption(ml: WaterConsumptionModel) {
    const data = await $axios({ url: $config.API_BASE_URL + 'trackWaterConsumption', data: ml, method: 'POST' })
        .then(response => {
            if (response.data.waterConsumption) {
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

export async function deleteAccount() {
    const data = await $axios({ url: $config.API_BASE_URL + 'deleteAccount', method: 'DELETE' })
        .then(resp => {
            if (resp.data.status) {
                remove("access_token")
                remove("is_setup")
                delete $axios.defaults.headers.common['Authorization']
                return true;
            } else {
                return false;
            }
        })
        .catch(err => {
            console.log(err);
            return false;
        })

    return data;
}
