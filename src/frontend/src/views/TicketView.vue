<template>
    <div id="ticket-view">
        <main>
            <div>
                <div id="message-name">
                    <router-link to="/" class="btn">Back</router-link>
                    <h3>{{ticket.ticketName}}</h3>
                </div>

                <div id="messages">
                  <!-- For each message in the ticketMessages, we'll show each of the message -->
                  <div v-for="message in ticketMessages" class="message">
                    <div class="message-top">
                      <div>
                        <img
                            src="../assets/cat.jpg"
                            alt="Profile Picture"
                        />
                        <h4>{{message.username}}</h4>
                      </div>

                      <div class="date">
                        <p>{{message.created}}</p>
                      </div>
                    </div>

                    <div class="message-bottom">
                      <p>
                        {{message.message}}
                      </p>
                    </div>
                  </div>
                </div>
            </div>

            <form id="message-box">
                <textarea v-model="ticketMessage.message" placeholder="Type a message..."></textarea>
                <div id="message-box-btns">
                  <InputText name="name" v-model="ticketMessage.username" placeholder="Your Name"/>
<!--                  <button type="button" id="default-btn">Default</button>-->
                    <button type="submit" id="submit-btn" @click.prevent="addMessage" :disabled="isSubmitDisabled">Submit</button>
                </div>
              <p v-if="errorMessage" class="error">{{errorMessage}}</p>
            </form>
        </main>

        <aside>
            <div>
                <h2>Ticket Overview</h2>
                <p>
                    Ticket ID: <a href="#"><span>{{ticket.id}}</span></a>
                </p>
                <p>Created: <span>{{ticket.created}}</span></p>
                <p>Last Message: <span>{{ticket.lastMessage}}</span></p>
                <p>Status: <span class="open">{{ticket.status}}</span></p>
                <p>Group ID: <span>{{ticket.groupID}}</span></p>
                <p>Priority: <span>{{ticket.priority}}</span></p>
                <p>Type: <span>{{ticket.type}}</span></p>
            </div>
            <div>
                <h2>Responsibility</h2>
            </div>
        </aside>
    </div>
</template>

<script>
import InputText from "primevue/inputtext";

export default {
  components: {InputText},
  data() {
    return {
      ticketMessages: [],
      ticket: {},
      ticketMessage: {
        message: "",
        created: "",
        username: "",
      },
      errorMessage: " ",
    };
  },
  watch: {
    'ticketMessage.message': 'validateMessage',
  },
  // With computed it automatically run when any of the dependencies they rely on change
  computed: {
    isSubmitDisabled() {
      return this.errorMessage !== "";
    }
  },
  methods: {
    validateMessage(newValue) {
      const character = /[`@#$%^&*()_+\-=[\]{};'"\\|<>/~]/;

      if (newValue.length < 50 || newValue.length > 700) {
        this.errorMessage = "Description must be at least 50 to 700 characters.";
      } else if (character.test(newValue)) {
        this.errorMessage = "Description can't contain special characters.";
      } else {
        this.errorMessage = "";
      }
    },
    // Fetch all the tickets, save it into tickets array
    getTicketMessages() {
      // "this.$route.params.index" will get the index number and ".index" is from routes and you can call it whatever
      // you want I just liked index XD
      fetch(`http://localhost:8080/ticket/${this.$route.params.index}/messages`)
          .then((res) => res.json())
          .then((data) => {
            this.ticketMessages = data;

            // TESTING
            // console.log(data);
          });
    },

    // This will get the current ticket information so that we can use it on the aside
    getTicket() {
      fetch(`http://localhost:8080/ticket/${this.$route.params.index}`)
          .then((res) => res.json())
          .then((data) => {
            this.ticket = data;

            // TESTING
            // console.log(data);
          });
    },

    // This will add the message to the database on the current ticket
    addMessage() {
      // gets the current date in this format MM-DD-YYYY | HH:MM
      const date = new Date();
      const currentDate = `${date.getMonth()}/${date.getDate()}/${date.getFullYear()} ${date.getHours()}:${date.
      getMinutes()}`
      this.created = currentDate;

      const ticketMessageCopy = {...this.ticketMessage, created:currentDate};

      fetch(`http://localhost:8080/add/message/${this.$route.params.index}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(ticketMessageCopy),
      }).then((data) => {
        // After a messaged is added we will run the getTicketMessages() function which then should allow it to show up
        // in the messages area without the user needing to refresh the page themselves :D
        this.getTicketMessages();
        this.ticketMessage.message = "";
        window.scrollTo(0, document.body.scrollHeight);

        // TESTING
        // console.log(data);
      });
    },
  },
  beforeMount() {
    this.getTicketMessages();
    this.getTicket();
  },
};
</script>