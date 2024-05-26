<!--
  Howdy Ming this is Ming :D
-->
<template>
    <Navbar></Navbar>
    <router-view></router-view>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import Button from "primevue/button";

export default {
    components: {
        Navbar,
    },
};
</script>
