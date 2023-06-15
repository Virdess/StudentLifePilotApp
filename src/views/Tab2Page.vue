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
          <ion-title size="large">Tab 1</ion-title>
        </ion-toolbar>
      </ion-header>
      <div style="margin: 20pt auto; width: 90%;">
        {{ days[now.getDay()] + ', ' + now.getDate() + ' ' + months[now.getMonth()] + ' ' + now.getFullYear() }}
        <h6>
          Расписание на завтра: 
        </h6>
      </div>
      <!--ion-datetime style="margin: 0 auto;" :day-values="now.getDate()" :first-day-of-week="1" :year-values="now.getFullYear()"
        :month-values="now.getMonth() + 1" presentation="date"></ion-datetime-->
      <ion-card style="margin-top: 20px; margin-bottom: 20px; padding: 10px;" v-for="lesson in lessons">
        <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
          <div>
            <p style="font-size: 16px; padding: 0; margin: 0;">{{ lesson.lesson }}</p>
            <p style="font-size: 12px; padding: 0; margin: 0;">{{ lesson.time }}</p>
          </div>
        </ion-card-title>
      </ion-card>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonDatetime, IonCard, IonCardTitle } from '@ionic/vue';
import ExploreContainer from '@/components/ExploreContainer.vue';
import { chevronBackOutline, chevronForwardOutline } from 'ionicons/icons';
// 👇️ const now: Date
const days = ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"];
const months = ['Января', 'Февраля', 'Марта', 'Апреля', 'Мая', 'Июня', 'Июля', 'Августа', 'Сентября', 'Октября', 'Ноября', 'Декабря']
let lessons = [
        {
          lesson: 'Обл. Технологии',
          time:'12:50'
        },
        {
          lesson: "Web-разработка",
          time: "14:20",
        },
        {
          lesson: '-',
          time:'15:40'
        },
        {
          lesson: 'Базы данных',
          time:'17:10'
        },
      ]
// получим текущую дату
let now = new Date();
// выведем в консоль день недели
console.log('Сегодня ' + days[now.getDay()] + ' ' + months[now.getMonth()], ' ' + now.getDate());

let lesson = "Название предмета"
let time = "00:00"
</script>

<style>

</style>