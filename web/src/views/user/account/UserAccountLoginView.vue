<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="Please eneter your username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="Please eneter your password" aria-describedby="passwordHelp">
                        <div id="passwordHelp" class="form-text">We'll never share your password with others.</div>
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '../../../router/index'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        // login实现页面跳转以及输出用户名密码错误
        const login = () => {
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'pk_index' });
                            console.log(store.state.user);
                        }
                    })
                },
                error() {
                    error_message.value = "Password or Username incorrect";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>

<style scoped>
button {
    width: 100%
}
div.error-message {
    color: red;
}
</style>