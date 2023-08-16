<template>
  <v-card
      class="mx-auto"
      style="max-width: 500px;"
  >
    <v-toolbar
        color="deep-purple-accent-4"
        cards
        dark
        flat
    >
      <v-btn icon>
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-card-title class="text-h6 font-weight-regular">
        회원가입
      </v-card-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-form
        ref="form"
        v-model="isValid"
        class="pa-4 pt-6"
    >
      <v-text-field
          v-model="email"
          :rules="[rules.email]"
          variant="filled"
          color="deep-purple"
          label="이메일"
          type="email"
      ></v-text-field>
      <v-text-field
          v-model="password"
          :rules="[rules.password, rules.length(8)]"
          variant="filled"
          color="deep-purple"
          counter="15"
          label="비밀번호"
          style="min-height: 96px"
          type="password"
      ></v-text-field>
      <v-text-field
          v-model="repassword"
          :rules="[rules.sameAs(password)]"
          variant="filled"
          color="deep-purple"
          counter="15"
          label="비밀번호 확인"
          style="min-height: 96px"
          type="password"
      ></v-text-field>
      <v-text-field
          v-model="nickname"
          variant="filled"
          color="deep-purple"
          label="닉네임"
      ></v-text-field>
      <v-textarea
          v-model="sentence"
          auto-grow
          variant="filled"
          color="deep-purple"
          label="개인정보이용약관"
          rows="1"
      ></v-textarea>
      <v-checkbox
          v-model="agreement"
          :rules="[rules.check]"
          color="deep-purple"
      >
        <template v-slot:label>
          개인정보 이용 약관에 동의합니다
        </template>
      </v-checkbox>
    </v-form>
    <v-divider></v-divider>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
          variant="text"
          @click.prevent="save"
      >
        확인
      </v-btn>
    </v-card-actions>
    <v-dialog
        v-model="dialog"
        max-width="400"
        persistent
    >
    </v-dialog>
  </v-card>
</template>

<script>
import PrivacyTerm from "./PrivacyTerm.vue";

export default {
  data: () => ({
    agreement: false,
    dialog: false,
    email: undefined,
    isValid: false,
    isLoading: false,
    password: undefined,
    repassword: undefined,
    nickname: undefined,
    sentence: PrivacyTerm.data().sentence,
    rules: {
      email: v => !!(v || '').match(/@/) || '이메일 형식을 입력하세요',
      length: len => v => (v || '').length >= len || `최소 ${len} 글자 이상 입력하세요`,
      password: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
          '비밀번호는 영소문자, 영대문자, 숫자, 특수문자를 포함해야 합니다',
      required: v => !!v || '필수값입니다',
      check: v => !!v || '약관에 동의해주세요',
      sameAs: pwd => v => (v === pwd) || '비밀번호가 일치하지 않습니다'
    },
  }),
  methods: {
    async save() {
      const validate = this.$refs.form.validate();
      console.log(validate);
      if ((await validate).valid) {
        if (confirm('가입하시겠습니까?')) {
          const params = {
            email: this.email,
            password: this.password,
            nickname: this.nickname
          }
          try {
            await this.$store.dispatch('signup', params);
            //this.$router.push('/');
          } catch (err) {
            console.log(err.code);
            if (err.code === 'ERR_NETWORK') {
              alert('네트워크 오류가 발생했습니다');
            }
          }
        }
      }
    }
  }
}
</script>
