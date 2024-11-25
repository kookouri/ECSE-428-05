<template>
  <div class="new-user">
    <h2>Create New Account</h2>
    <form @submit.prevent="createUser">
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="user.email" required />
      </div>
      <div>
        <label for="name">Name:</label>
        <input type="text" v-model="user.name" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="user.password" required />
      </div>
      <div>
        <label for="phoneNumber">Phone Number:</label>
        <input type="tel" v-model="user.phoneNumber" required />
      </div>
      <button type="submit">Create Account</button>
    </form>

    <p v-if="message">{{ message }}</p>
  <Footer/>
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
        email: '',
        name: '',
        password: '',
        phoneNumber: ''
      },
      message: ''
    };
  },
  methods: {
    async createUser() {
      try {
        const response = await client.post('/users', this.user);

        if (response.status === 201) {
          this.$cookies.set('username', this.user.email);
          this.$cookies.set('password',  this.user.password);
    
          console.log('Created new cookies:');
          console.log('username: ', decodeURIComponent(this.$cookies.get('username')));
          console.log('password: ', this.$cookies.get('password'));
                    
          this.$router.push('/');
        } else {
          this.message = `Error: ${response.data.message}`;
        }
      } catch (error) {
        this.message = `Error: ${error.message} `;
      }
    }
  }
};
</script>

<style scoped>
.new-user {
  max-width: 400px;
  margin: 0 auto;
}
label {
  display: block;
  margin: 5px 0;
}
input {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
}
button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
