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
                    Вход
                </IonTitle>
                <form novalidate id="loginform" @submit.prevent="login">
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="userName" labelPlacement="floating"
                            placeholder="Введите свой username" type="text" id="email" v-model="userName" name="email"
                            autocapitalize="off" required></ion-input>
                    </ion-item>
                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="Пароль" labelPlacement="floating"
                            placeholder="Введите пароль" type="password" id="password" v-model="password" name="password"
                            autocapitalize="off" required></ion-input>
                    </ion-item>
                    <ion-list>
                        <ion-item class="input_no_border">
                            <ion-button style="margin: 0 auto; width: 180px; height: 25px; padding:0;" id="loginBtn"
                                type="submit" expand="block">Войти</ion-button>
                        </ion-item>
                        <ion-item class="input_no_border">
                            <ion-button style="margin: 0 auto;width: 180px; height: 25px; padding:0;" color="light"
                                href="/register">Зарегистрироваться</ion-button>
                        </ion-item>

                    </ion-list>
                </form>
            </div>
        </IonContent>
    </ion-page>
</template>
  
<script lang="ts">
import {
    IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController,
} from '@ionic/vue';
import { defineComponent } from "vue";

import { showToast } from "@/utils";

import { login } from "@/service/AuthService";
export default defineComponent({
    name: "LoginPage",
    components: { IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController },
    data() {
        return {
            userName: "",
            password: "",
            onceSubmitted: false,
            disableSend: false
        };
    },
    methods: {
        async showLoading() {
            const loading = await loadingController
                .create({
                    message: 'Пожалуйста, подождите...',
                });

            await loading.present();
        },
        async login() {
            this.onceSubmitted = true;

            let userName = this.userName;
            let password = this.password;

            await this.showLoading();
            this.disableSend = false;
            let loginSuccessful = await login({ userName, password });
            this.disableSend = false;

            loadingController.dismiss()

            if (loginSuccessful) {
                this.$router.push("/tabs");
                showToast("Авторизация прошла успешно!");
                this.onceSubmitted = false;
                this.userName = "";
                this.password = "";
            } else {
                this.password = "";
                showToast("Авторизация не удалась!");
            }

        },
    }
})
</script>

<script lang="ts">

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