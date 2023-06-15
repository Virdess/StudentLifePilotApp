<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Study Machine</ion-title>
            </ion-toolbar>
        </ion-header>
        <IonContent style="text-align: center; height: 100vh;">
            <div style="margin-top: 150px;">
                <IonTitle>
                    Регистрация
                </IonTitle>
                <form novalidate id="loginform" @submit.prevent="register">
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="userName" labelPlacement="floating"
                            placeholder="Введите свой username" type="text" id="email" v-model="userName" name="email"
                            autocapitalize="off" required></ion-input>
                    </ion-item>
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="e-mail" labelPlacement="floating"
                            v-model="email" name="email" autocapitalize="off" required placeholder="Введите свой e-mail"
                            type="email" id="email"></ion-input>
                    </ion-item>
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="Телефон" labelPlacement="floating"
                            placeholder="Введите номер телефона" type="number" v-model="phone" name="phone" required
                            id="phone"></ion-input>
                    </ion-item>
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="Группа" labelPlacement="floating"
                            placeholder="Введите код группы" type="text" v-model="group" name="group" required
                            id="phone"></ion-input>
                            <ion-select aria-label="Выберите группу" placeholder="Выберите группу" v-model="groupsList">
                        <ion-select-option :value="group" v-for="group in groupsList" id="day" :key="group">{{ group.groupName
                        }}</ion-select-option>
                    </ion-select>
                    </ion-item>
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="Пароль" labelPlacement="floating"
                            placeholder="Введите пароль" type="password" v-model="password" name="password" required
                            id="password"></ion-input>
                    </ion-item>
                    <ion-list>
                        <ion-item class="input_no_border">
                            <ion-button style="margin: 0 auto; width: 150px; height: 25px; padding:0;"
                                type="submit">Регистрация</ion-button>
                        </ion-item>
                        <ion-item class="input_no_border">
                            <ion-button style="margin: 0 auto;width: 150px; height: 25px; padding:0;" color="light"
                                href="/">Вход</ion-button>
                        </ion-item>
                    </ion-list>
                </form>
            </div>
        </IonContent>
    </ion-page>
</template>
  
<script lang="ts">
import { IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController, IonSelect, IonSelectOption } from '@ionic/vue';

import { defineComponent } from "vue";
import { register } from "@/service/AuthService";
import { showToast } from "@/utils";
import {getAllGroups} from '@/service/UserService'
export default defineComponent({
    components: { IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController, IonSelect, IonSelectOption },
    name: "Register",
    data() {
        return {
            phone: '',
            userName: '',
            email: "",
            password: "",
            group:'',
            onceSubmitted: false,
            groupsList:[{_id:'', groupName:''}]
        };
    },
    methods: {
        async getGroups(){
            let asas = await getAllGroups()
            this.groupsList = asas
        },
        async showLoading() {
            const loading = await loadingController.create({
                message: "Пожалуйста, подождите...",
            });

            await loading.present();
        },
        async register() {
            this.showLoading
            this.onceSubmitted = true;


            await this.showLoading();
            let userName = this.userName;
            let phone = this.phone;
            let email = this.email;
            let password = this.password;
            let group = this.group

            let registerSuccessful = await register({
                userName,
                phone,
                email,
                password,
                group
            });

            loadingController.dismiss();
            if (registerSuccessful) {
                this.$router.push("/login");
                showToast("Успешная регистрация!");
                this.onceSubmitted = false;
                this.userName = '';
                this.phone = '';
                this.email = "";
                this.password = "";
                this.group = ""
            } else {
                showToast("Регистрация не удалась!");
            }
        },
    },
    mounted(){
        this.getGroups()
    }
});
</script>


<style>
.input_no_border {
    border: none !important;
    --inner-border-width: 0;
}

.input_border_bottom {
    border-bottom: 1px solid rgb(187, 187, 187);
}
</style>