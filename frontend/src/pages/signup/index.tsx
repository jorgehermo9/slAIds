import { Nav } from "@/components/Nav/Nav";
import { useState } from "react";
import styles from "../styles/login.module.scss";

export default function Signup() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");

  const handleSubmit = () => {
    const user = {
      userName,
      password,
      email,
    };
  };

  return (
    <>
      <Nav />
      <div className={styles.cardContainer}>
        <div className={styles.card}>
          <div className={styles.formField}>
            <label htmlFor="usernameInput" className={styles.title}>
              Username
            </label>
            <input
              value={userName}
              type="text"
              id="usernameInput"
              className={styles.input}
              placeholder="user12"
              onChange={(e) => {
                setUserName(e.target.value);
              }}
            />
          </div>
          <div className={styles.formField}>
            <label htmlFor="passwordInput" className={styles.title}>
              Password
            </label>
            <input
              value={password}
              type="password"
              id="passwordInput"
              className={styles.input}
              placeholder="%der351"
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
          </div>
          <div className={styles.formField}>
            <label htmlFor="emailInput" className={styles.title}>
              E-mail
            </label>
            <input
              value={email}
              type="text"
              id="emailInput"
              className={styles.input}
              placeholder="user12@gmail.com"
              onChange={(e) => {
                setEmail(e.target.value);
              }}
            />
          </div>

          <button className={styles.buttonSignup} onClick={handleSubmit}>
            Create account
          </button>
        </div>
      </div>
    </>
  );
}
