import { Nav } from "@/components/Nav/Nav";
import { useStorage } from "@/hooks/useStorage";
import UserService from "@/services/UserService";
import { Router, useRouter } from "next/router";
import { useEffect, useState } from "react";
import styles from "../styles/login.module.scss";

export default function Signup() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [token, setToken] = useState("");
  const router = useRouter();

  useEffect(() => {
    if (token !== "") {
      sessionStorage.setItem("serviceToken", token);
    }
  }, [token]);

  const handleSubmit = () => {
    const user = {
      userName,
      password,
      email,
    };

    UserService.signup(user).then((res) => {
      setToken(res);
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

          <button className={styles.buttonSignup} onClick={handleSubmit}>
            Create account
          </button>
        </div>
      </div>
    </>
  );
}
