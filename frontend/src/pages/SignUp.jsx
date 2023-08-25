import React, { useState } from "react";
import useForm from "../hooks/useForm";

export default function SignUp() {
  const [member, setMember] = useState({});
  const handleSubmit = useForm({url: "/signUp", data: member});
  const handleChange = (e) => {
    const { name, value } = e.target;
    setMember((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <input
          type="email"
          name="email"
          className="border"
          placeholder="이메일"
          required
          onChange={handleChange}
        />
      </div>
      <div>
        <input type="text" className="border" placeholder="닉네임" />
      </div>
      <div>
        <select name="position">
          <option>탑(TOP)</option>
          <option>정글(JUNGLE)</option>
          <option>미드(MID)</option>
          <option>원딜(ADC)</option>
          <option>서포터(SPT)</option>
        </select>
      </div>
      <div>
        <input
          type="password"
          name="password"
          className="border"
          placeholder="비밀번호"
        />
      </div>
      <div>
        <input
          type="password"
          name="re-password"
          className="border"
          placeholder="비밀번호 확인"
        />
      </div>
      <button>확인</button>
    </form>
  );
}
