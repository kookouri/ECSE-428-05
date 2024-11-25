<template>
    <div class="new-user">
      <h2>Sign In To Existing Account</h2>
      <form @submit.prevent="login">
        <div>
          <label for="username">Email:</label>
          <input type="text" v-model="user.username" required />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" v-model="user.password" required />
        </div>
        <button type="submit">Log In</button>
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
          username: '',
          password: '',
        },
        message: ''
      };
    },
    methods: {
      async login() {
        try {
          const response = await client.post('/login', this.user);
  
          if (response.status === 202) {
            this.message = `Account logged in successfully: ${response.data.name}`;
            // Save cookies and change page
            this.$cookies.set('username', this.user.username);
            this.$cookies.set('password', this.user.password);

            this.fetchUser();

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
      },
      fetchUser() {
        fetch(backendUrl + `/users?email=${this.$cookies.get('username')}`, { 
            method: 'GET',
            redirect: 'manual'
        })
        .then(response => response.text())
        .then(data => {
          data = JSON.parse(data);
          this.$cookies.set('id', data.accounts[0].id);
          this.$cookies.set('name', data.accounts[0].name);
        })
        .catch(error => {
          console.error('Error fetching user:', error);
        });
      },
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
  