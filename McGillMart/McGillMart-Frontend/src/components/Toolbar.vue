<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="dark" variant="danger">
        <b-navbar-brand>
          <router-link to="/" class="text-light">McGillMart</router-link>
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <ul class="navbar-nav ml-auto">
            <li v-if="!isLoggedIn" class="nav-item">
              <router-link to="/login" class="nav-link">Log In</router-link>
            </li>
            <li v-if="!isLoggedIn" class="nav-item">
              <router-link to="/registration" class="nav-link"
                >Sign Up</router-link
              >
            </li>
            <li v-if="isLoggedIn" class="nav-item">
              <div v-if="isLoggedIn" class="nav-item dropdown">
                <a
                  class="nav-link dropdown-toggle"
                  href="#"
                  id="navbarDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  My Account
                </a>
                <div
                  class="dropdown-menu"
                  aria-labelledby="navbarDropdown"
                  :style="{
                    transition: 'background-color 1.5s',
                    zIndex: 1,
                  }"
                >
                  <router-link class="dropdown-item" to="/my-profile"
                    ><b-icon icon="person"></b-icon>My Profile</router-link>
                  <router-link class="dropdown-item" to="/my-cart"
                    ><b-icon icon="cart"></b-icon>View Cart</router-link>
                  <a
                    class="dropdown-item"
                    @click.prevent="signOut"
                    style="color: var(--color-red)"
                    ><b-icon
                      icon="box-arrow-right"
                      style="color: var(--color-red)"
                    ></b-icon>Sign Out
                  </a>
                </div>
              </div>
            </li>
          </ul>
        </b-collapse>
      </b-navbar>
    </div>
    <router-view />
  </div>
</template>

<script>

export default {
  data() {
    return {
      mounted: false,
      isLoggedIn: false,
      searchQuery: "",
    };
  },
  mounted() {
    this.isLoggedIn = this.checkAuthenticationStatus();
  },
  methods: {
    checkAuthenticationStatus() {
      // Check if user is authenticated based on your authentication logic
      const username = this.$cookies.get("username");
      const password = this.$cookies.get("password");

      console.log(`Username: ${username}`);
      console.log(`Password: ${password}`);
      return username && password;
    },
    signOut() {
      this.isLoggedIn = false;
      this.$cookies.remove("username");
      this.$cookies.remove("password");
      this.$router.push("/");
    },
    onSearch() {
      this.$emit("search-items", this.searchQuery);
    },
  },
};
</script>

<style scoped></style>
