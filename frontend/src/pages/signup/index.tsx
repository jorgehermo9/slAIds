import { Nav } from "@/components/Nav/Nav";
import UserService from "@/services/UserService";
import { Router, useRouter } from "next/router";
import { useEffect, useState } from "react";
import styles from "../styles/login.module.scss";

export default function Signup() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [tokenSetter, setTokenSetter] = useState("");
  const router = useRouter();

  useEffect(() => {
    if (sessionStorage.getItem("serviceToken") !== null) {
      router.push("/");
    }
  }, [router]);

  useEffect(() => {
    if (tokenSetter !== "") {
      sessionStorage.setItem("serviceToken", tokenSetter);
    }
  }, [tokenSetter]);

  const handleSubmit = () => {
    const user = {
      userName,
      password,
      email,
    };

    UserService.signup(user).then((res) => {
      setTokenSetter(res);
      router.push("/");
    });
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

          <button className={styles.button} onClick={handleSubmit}>
            Create account
          </button>
        </div>
      </div>
    </>
  );
}
