<template>
    <ion-page>

        <ion-header>
            <ion-toolbar>
                <ion-title>Study Machine</ion-title>
            </ion-toolbar>
        </ion-header>
        <IonContent style="text-align: center;">
            <div style="margin-top: 50px;">
                <IonTitle>
                    Заявка на замену
                </IonTitle>
                <form novalidate id="loginform" @submit.prevent="addQuery">
                    <ion-list>
                        <h5>
                            История заявок
                        </h5>
                        <ion-item v-if="Object.keys(queries).length" v-for="query in queries.slice().reverse()">
                            <ion-card style="width: 100%;">
                                <ion-card-header>
                                    <ion-card-title>{{ query.title }}</ion-card-title>
                                    <ion-card-subtitle>{{ query.group }}</ion-card-subtitle>
                                </ion-card-header>

                                <ion-card-content>
                                    <b>{{ query.obj }}</b>
                                    <p>{{ query.month }}, {{ query.day }}</p>
                                    <div style="width: 100%; text-align: end;">
                                        {{ query.comment }}
                                    </div>
                                </ion-card-content>
                                <div style="width: 100%; text-align: end;">
                                    <ion-button color="success">Принять</ion-button><ion-button color="danger">Отклонить</ion-button>
                                </div>
                            </ion-card>
                        </ion-item>
                    </ion-list>
                </form>
            </div>
        </IonContent>
    </ion-page>
</template>
  
<script lang="ts">
import {
    IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController, IonDatetime, IonSelect, IonSelectOption, IonRadio, IonRadioGroup, IonCard, IonCardContent, IonCardHeader, IonCardTitle, IonCardSubtitle
} from '@ionic/vue';
import { defineComponent, ref } from "vue";

import { showToast } from "@/utils";

import { login } from "@/service/AuthService";

export default defineComponent({
    name: "ChangePage",
    components: { IonContent, IonPage, IonTitle, IonHeader, IonToolbar, IonInput, IonItem, IonList, IonButton, loadingController, IonDatetime, IonSelect, IonSelectOption, IonRadio, IonRadioGroup, IonCard, IonCardContent, IonCardHeader, IonCardTitle, IonCardSubtitle },
    data() {
        return {
            comment: "",
            password: "",
            onceSubmitted: false,
            disableSend: false,
            months: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            days: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'],
            groups: ['П-19-47Б'],
            selectedTitle: '',
            queries: [
                {
                    title: 'Заменить',
                    obj: 'Облачные технологии',
                    day: 'Понедельник',
                    month: 'Май',
                    group: 'П-19-47Б',
                    comment: 'По состоянию здоровья'
                },
                {
                    title: 'Убрать',
                    obj: 'Облачные технологии',
                    day: 'Понедельник',
                    month: 'Май',
                    group: 'П-19-47Б',
                    comment: 'По состоянию здоровья'
                },
                {
                    title: 'Убрать',
                    obj: 'Проектирование',
                    day: 'Суббота',
                    month: 'Октябрь',
                    group: 'П-19-47Б',
                    comment: 'По состоянию здоровья'
                },
                {
                    title: 'Добавить',
                    obj: 'Веб-дизайн',
                    day: 'Суббота',
                    month: 'Октябрь',
                    group: 'П-19-47Б',
                    comment: 'По состоянию здоровья'
                },
            ],
            objects: ['Web-разработка', 'Облачные технологии', 'Веб-дизайн', 'Проектирование'],
            selectedMonth: '', // Значение выбранного месяца
            selectedDay: '', // Значение выбранного дня
            selectedObject: '', // Значение выбранного предмета
            selectedGroup: '',
            title: '',
        };
    },
    methods: {
        addQuery() {

            const selectedMonthValue = this.selectedMonth;
            const selectedDayValue = this.selectedDay;
            const selectedObjectValue = this.selectedObject;
            const selectedGroupValue = this.selectedGroup;
            const selectedTitle = this.title;
            const selectedCommentValue = this.comment;
            console.log(selectedDayValue)
            if (selectedMonthValue != '' && selectedDayValue != '' && selectedObjectValue != '') {
                this.queries.push({
                    title: selectedTitle,
                    obj: selectedObjectValue,
                    day: selectedDayValue,
                    month: selectedMonthValue,
                    group: selectedGroupValue,
                    comment: selectedCommentValue
                })
                return;
            }
        }
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