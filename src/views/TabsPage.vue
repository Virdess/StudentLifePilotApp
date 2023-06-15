<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="tab1" href="/tabs/tab1">
          <ion-icon aria-hidden="true" :icon="home" />
          <ion-label>Главная</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab2" href="/tabs/tab2">
          <ion-icon aria-hidden="true" :icon="fileTrayFullOutline" />
          <ion-label>Расписание</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tab3" href="/tabs/tabChange" v-if="role == 'ROLE_TEACHER'">
          <ion-icon aria-hidden="true" :icon="refreshOutline" />
          <ion-label>Замены</ion-label>
        </ion-tab-button>

        <!--ion-tab-button tab="tab3" href="/tabs/tab3">
          <ion-icon aria-hidden="true" :icon="personOutline" />
          <ion-label>Профиль</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="tab4" href="/tabs/tab4">
          <ion-icon :icon="chatbubblesOutline"></ion-icon>
          <ion-label>Чат</ion-label>
        </ion-tab-button-->
      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script lang="ts">
import { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet } from '@ionic/vue';
import { chatbubblesOutline, ellipse, fileTrayFullOutline, home, personOutline, refreshOutline, square, triangle } from 'ionicons/icons';
import { DefineComponent, defineComponent } from 'vue';
import { getUserData } from '@/service/UserService';
import { get, set, remove } from "../helper/storage";
export default defineComponent({
  components: { IonTabBar, IonTabButton, IonTabs, IonLabel, IonIcon, IonPage, IonRouterOutlet },
  data() {
    return {
      chatbubblesOutline,
      ellipse,
      fileTrayFullOutline,
      home,
      personOutline,
      square,
      triangle,
      refreshOutline,
      role:''
    }
  },
  methods:{
    async getRole(){
      await getUserData()
      const username = await get("username")
      const role = await get("role")
      console.log(username, role)
      this.role = role
      console.log(role)
    }
  },
  mounted(){
    this.getRole()
  }
})
</script>
