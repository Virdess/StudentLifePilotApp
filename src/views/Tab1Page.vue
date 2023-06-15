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
      <div style="width: 90%; margin: 0 auto;">

        <h4>Здравствуй, {{ username }}</h4>
        <p>П-19-47Б</p>

        <!--ion-card style="margin-top: 10vi; margin-bottom: 20px;">
          <ion-card-title style="display: flex;justify-content: space-around; font-size: 28px;">
            <a href="/tabs/tab3" style="color: black; text-decoration: none;">
              <div style="text-align: center;">
                <ion-icon aria-hidden="true" :icon="personOutline" />
                <p style="font-size: 10px; padding: 0; margin: 0;">Профиль</p>
              </div>
            </a>
            <a href="/tabs/tab2" style="color: black; text-decoration: none;">
              <div style="text-align: center;">
                <ion-icon :icon="fileTrayFullOutline"></ion-icon>
                <p style="font-size: 10px; padding: 0; margin: 0;">Расписание</p>
              </div>
            </a>
            <a href="/rating" style="color: black; text-decoration: none;">
              <div style="text-align: center;">
                <ion-icon :icon="flashOutline"></ion-icon>
                <p style="font-size: 10px; padding: 0; margin: 0;">Успеваемость</p>
              </div>
            </a>
            <a href="/tabs/tab3" style="color: black; text-decoration: none;">
              <div style="text-align: center;">
                <ion-icon :icon="starOutline"></ion-icon>
                <p style="font-size: 10px; padding: 0; margin: 0;">Достижения</p>
              </div>
            </a>
          </ion-card-title>
        </ion-card-->

        <h7>Следующая пара:</h7>

        <ion-card style="margin-top: 8vi; margin-bottom: 10vi; padding: 10px;">
          <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
            <div>
              <p style="font-size: 16px; padding: 0; margin: 0;">{{ lessons[0].lesson }}</p>
              <p style="font-size: 12px; padding: 0; margin: 0;">{{ lessons[0].time }}</p>
            </div>
          </ion-card-title>
        </ion-card>

        <h7>Расписание сегодня:</h7>

        <ion-card style="margin-top: 8vi; margin-bottom: 10vi; padding: 10px;" v-for="lesson in lessons">
          <ion-card-title style="display: flex;justify-content: space-between; font-size: 28px; align-items: center;">
            <div>
              <p style="font-size: 16px; padding: 0; margin: 0;">{{ lesson.lesson }}</p>
              <p style="font-size: 12px; padding: 0; margin: 0;">{{ lesson.time }}</p>
            </div>
          </ion-card-title>
        </ion-card>

        <!--h7>Вас могут заинтересовать:</h7>
        <ion-item v-for="items in interestingItems">
          <ion-thumbnail slot="start">
            <img alt="Silhouette of mountains" :src=items.image />
          </ion-thumbnail>
          <ion-label> {{ items.title }} </ion-label>
        </ion-item-->
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonAvatar, IonItem, IonLabel, IonThumbnail, IonCard, IonCardHeader, IonCardTitle } from '@ionic/vue';
import { defineComponent } from 'vue';
import { ellipse, square, triangle, personOutline, fileTrayFullOutline, flashOutline, starOutline, chevronForwardOutline } from 'ionicons/icons';
import ExploreContainer from '@/components/ExploreContainer.vue';
import { getUserData } from '@/service/UserService';
import { tSExpressionWithTypeArguments } from '@babel/types';
import { get, set, remove } from "../helper/storage";
let username = ""
let lesson = "Web-разработка"
let time = "12:50"

let interestingItems = {}
export default defineComponent({
  components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonIcon, IonAvatar, IonItem, IonLabel, IonThumbnail, IonCard, IonCardHeader, IonCardTitle },

  data() {
    return {
      interestingItems: {
        item1: {
          image: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Eo_circle_blue_number-1.svg/2048px-Eo_circle_blue_number-1.svg.png',
          title: 'User первый'
        },
        item2: {
          image: 'https://cdn-st1.rtr-vesti.ru/vh/pictures/lw/427/810/1.jpg',
          title: 'User второй'
        },
        item3: {
          image: 'data:image/png;base64,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',
          title: 'User третий'
        }
      },
      username: "",
      lessons: [
        {
          lesson: "Web-разработка",
          time: "12:50",
        },
        {
          lesson: 'Обл. Технологии',
          time:'14:20'
        },
        {
          lesson: 'Обл. Технологии',
          time:'15:40'
        },
        {
          lesson: 'ТРПО',
          time:'17:10'
        },
      ],
      personOutline,
      fileTrayFullOutline,
      flashOutline,
      starOutline,
      chevronForwardOutline
    }
  },
  methods: {
    async getUserName() {
      await getUserData();
      const username = await get("username")
      const role = await get("role")
      console.log(username, role)
      this.username = username
    }
  },
  mounted() {
    this.getUserName()
  }
})
</script>