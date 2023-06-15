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

                    <ion-select aria-label="Месяц" placeholder="Выберите месяц" v-model="selectedMonth">
                        <ion-select-option :value="month" v-for="month in months" id="month" :key="month">{{ month
                        }}</ion-select-option>
                    </ion-select>

                    <ion-select aria-label="fruit" placeholder="Выберите день недели" v-model="selectedDay">
                        <ion-select-option :value="day" v-for="day in days" id="day" :key="day">{{
                            day
                        }}</ion-select-option>
                    </ion-select>

                    <ion-select aria-label="fruit" placeholder="Выберите предмет" v-model="selectedObject">
                        <ion-select-option :value="object" v-for="object in objects" id="day" :key="object">{{ object
                        }}</ion-select-option>
                    </ion-select>

                    <ion-select aria-label="fruit" placeholder="Выберите предмет" v-model="selectedGroup">
                        <ion-select-option :value="group" v-for="group in groups" id="day" :key="group">{{ group
                        }}</ion-select-option>
                    </ion-select>

                    <ion-radio-group id="isDoub" style="display: flex; align-items: center; justify-content: space-around; margin: 10px;">
                        <div style="display: flex; align-items: center;">

                            <ion-radio value="replace" labelPlacement="end" />Четный
                        </div>
                        <div style="display: flex; align-items: center;">
                            <ion-radio value="add" labelPlacement="end" text="asdsd" />Нечётный

                        </div>
                    </ion-radio-group>

                    <ion-radio-group id="todo" v-model="title"
                        style="display: flex; align-items: center; justify-content: space-around;margin: 10px;">
                        <div style="display: flex; align-items: center;">
                            <ion-radio value="Заменить" labelPlacement="end" />Заменить
                        </div>
                        <div style="display: flex; align-items: center;">
                            <ion-radio value="Добавить" labelPlacement="end" text="asdsd" />Добавить
                        </div>
                        <div style="display: flex; align-items: center;">
                            <ion-radio value="Убрать" labelPlacement="end" />Убрать
                        </div>
                    </ion-radio-group>

                    <ion-item class="input_no_border">
                        <ion-input class="input_border_bottom input_no_border" label="comment" labelPlacement="floating"
                            placeholder="Комментарий к заявке" type="text" id="email" v-model="comment" name="comment"
                            autocapitalize="off" required></ion-input>
                    </ion-item>




                    <ion-list>
                        <ion-item class="input_no_border">
                            <ion-button style="margin: 0 auto; width: 150px; height: 25px; padding:0;" id="loginBtn"
                                type="submit" expand="block">Подать заявку</ion-button>
                        </ion-item>
                    </ion-list>
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