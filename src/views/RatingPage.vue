<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Study Machine</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content :fullscreen="true">
            <div style="margin: 20pt auto; width: 90%; font-size: 20px;">
                <a href="/tabs">
                    <ion-icon :icon="chevronBackOutline"></ion-icon>
                </a>
            </div>
            <div style="margin: 20pt auto; width: 90%;">
                <h4>Успеваемость</h4>
            </div>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
            <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;">
                <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
                    <div>
                        <p style="font-size: 16px; padding: 0; margin: 0;">Название предмета</p>
                    </div>
                    <div
                        style="background-color: #0984e3; height: 40px; width: 40px; display: flex; border-radius: 5px; color: white;">
                        <h6 style="padding: 0; margin: auto;">
                            100
                        </h6>
                    </div>
                </ion-card-title>
            </ion-card>
        </ion-content>
    </ion-page>
</template>
  
<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonAvatar, IonItem, IonLabel, IonThumbnail } from '@ionic/vue';
import { defineComponent } from 'vue';
import { ellipse, square, triangle, personOutline, fileTrayFullOutline, flashOutline, starOutline, chevronForwardOutline, chevronBackOutline } from 'ionicons/icons';
import ExploreContainer from '@/components/ExploreContainer.vue';

</script>
  