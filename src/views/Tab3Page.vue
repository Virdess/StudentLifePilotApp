<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Study Machine</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Tab 3</ion-title>
        </ion-toolbar>
      </ion-header>

      <div style="width: 90%; margin: 20pt auto; text-align: center;">
        <ion-avatar style="margin: 0 auto;">
          <img alt="Silhouette of a person's head" src="https://ionicframework.com/docs/img/demos/avatar.svg" />
        </ion-avatar>
        <h5>
          <b>
            Имя Фамилия
          </b>
        </h5>
        <p>
          Учебное заведение
        </p>
      </div>
      <div style="margin: 20pt auto; width: 90%;">
        <div style="display: flex; justify-content: space-between;">
          <b>
            Основная информация
          </b>
          <ion-icon aria-hidden="true" :icon="pencilOutline" />
        </div>
        <div style="width: 100%; text-align: center; border-bottom: 1px solid #dfdfdf; border-radius: 15px;">

          <p>
            Имя Фамилия Отчествович
          </p>
          <p>
            999 лет
          </p>
          <p>
            Город
          </p>
          <p>
            email@example.com
          </p>
          <p>
            88005553535
          </p>
        </div>
        <b>
          О себе
        </b>

        <textarea
          style="border: 1px solid #dfdfdf ; height: 80px; border-radius: 10px; width: 100%; background: none; padding: 5% 2%;"></textarea>
        <b>
          Специальность
        </b>
        <div style="width: 100%; text-align: center; border-bottom: 1px solid #dfdfdf; border-radius: 15px;">

          <p>
            Специальность учащегося
          </p>
        </div>
        <b>
          Навыки
        </b>
        <textarea
          style="border: 1px solid #dfdfdf; height: 80px; border-radius: 10px; width: 100%; background: none; padding: 5% 2%;"></textarea>
      </div>
      <div style="margin: 20pt auto; width: 90%; text-align: center;">
        <ion-button>
          Сохранить
        </ion-button>
      </div>
      <div style="margin: 20pt auto; width: 90%; text-align: center;">
        <ion-button color="danger">
          Выход
        </ion-button>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonInput, IonAvatar, IonButton, IonIcon } from '@ionic/vue';
import { pencilOutline } from 'ionicons/icons';
</script>
<style>
::placeholder {
  color: black;
}

:-ms-input-placeholder {
  /* Internet Explorer 10-11 */
  color: black;
}

::-ms-input-placeholder {
  /* Microsoft Edge */
  color: black;
}
</style>