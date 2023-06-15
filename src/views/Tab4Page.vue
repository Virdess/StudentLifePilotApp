<template>
    <ion-page>
      <ion-header>
        <ion-toolbar>
          <ion-title>Study Machine</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-content :fullscreen="true">
        <ExploreContainer name="Чат"/>
      </ion-content>
    </ion-page>
  </template>
  
  <script setup lang="ts">
  import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonAvatar, IonItem, IonLabel, IonThumbnail } from '@ionic/vue';
  import { defineComponent } from 'vue';
  import { ellipse, square, triangle, personOutline, fileTrayFullOutline, flashOutline, starOutline, chevronForwardOutline } from 'ionicons/icons';
  import ExploreContainer from '@/components/ExploreContainer.vue';
  </script>
  