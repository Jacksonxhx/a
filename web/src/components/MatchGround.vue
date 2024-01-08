<template>
    <div class="matchground">
       <div class="row">
        <div class="col-6">
            <div class="user-photo">
                <img :src="$store.state.user.photo" alt="">
            </div>
            <div class="user-username">
                {{ $store.state.user.username }}
            </div>
        </div>
        <div class="col-6">
            <div class="user-photo">
                <img :src="$store.state.pk.opponent_photo" alt="">
            </div>
            <div class="user-username">
                {{ $store.state.pk.opponent_username }}
            </div>
        </div>
        <div class="col-12" style="text-align: center; padding-top: 10vh;"> 
            <button @click="click_match_btn" type="button" class="btn btn-warning btn-lg">{{ match_btn_info }}</button>
        </div>
       </div>
    </div>
</template>

<script>
import store from '@/store';
import { ref } from 'vue'

export default {
    setup() {
        let match_btn_info = ref("Matching");

        const click_match_btn = () => {
            if (match_btn_info.value === "Matching"){
                match_btn_info.value = "Cancel";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));
            } else {
                match_btn_info.value = "Matching";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        return {
            match_btn_info,
            click_match_btn,
        }
    }
}
</script>

<style scoped>
div.matchground {
    width: 60vw;
    height: 50vh;
    margin: 40px auto;
    background-color: rgba(50, 50, 50, 0.5);
}

div.user-photo {
    text-align: center;
    padding-top: 5vh;
}

div.user-photo > img {
    border-radius: 50%;
    width: 15vh;
}

div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 1vh;
}
</style>
