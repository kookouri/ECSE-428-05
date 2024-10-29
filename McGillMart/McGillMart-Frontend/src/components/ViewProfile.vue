<template>
    <div>
        <toolbar/>
        <div id="view-profile-component">
            <h2 style="margin-top: 10%; color: #fc0339">NAME: {{user.name}}</h2>
            <hr/>
            <div id="profile-values">
                <p><b>Email:</b></p>
                <p style="margin-bottom: 2%;"> {{user.email}} </p>
                <p><b>Phone Number:</b></p>
                <p style="margin-bottom: 2%;"> {{user.phoneNumber}} </p>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    data() {
        return {
            user: {
                name: "",
                phoneNumber: "",
                email: ""
            }
        }
    },
    async created() {
        try {
            // Assuming username is the user's email
            const response = await client.get('/users/account?email=' + this.$cookies.get('username'));
            if (response.data.accounts[0].errorMessage !== null) {
                alert(response.data.errorMessage);
            } else {
                this.user.name = response.data.accounts[0].name.toUpperCase();
                this.user.phoneNumber = response.data.accounts[0].phoneNumber;
                this.user.email = response.data.accounts[0].email;
            }
        } catch (e) {
            alert(e);
        }
    }
}

</script>

<style scoped>

p {
    margin-bottom: 0%;    
}

#view-profile-component {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

#profile-values {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 70%;
    text-align: left;
}

hr {
    display: block;
    height: 1px;
    border: 0;
    border-top: 1px solid #ccc;
    margin: 1em 0;
    padding: 0;
    width: 70%;
}
</style>