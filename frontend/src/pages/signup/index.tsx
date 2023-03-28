import { Nav } from "@/components/Nav/Nav";
import { Title } from "@/components/Title/Title";
import UserService from "@/services/UserService";
import { Router, useRouter } from "next/router";
import { FormEvent, useEffect, useState } from "react";
import styles from "../styles/login.module.scss";

export default function Signup() {
  const [userSingupRequest, setUserSingupRequest] = useState({
    userName: "",
    password: "",
    email: "",
  });
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

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    UserService.signup(userSingupRequest).then((res) => {
      setTokenSetter(res);
      router.push("/");
    });
  };

  return (
    <>
      <div className={styles.cardContainer}>
        <Title />

        <form className={styles.card} onSubmit={handleSubmit}>
          <h2 className={styles.formTitle}>Signup</h2>

          <div className={styles.formField}>
            <label htmlFor="usernameInput" className={styles.title}>
              Username
            </label>
            <input
              value={userSingupRequest.userName}
              type="text"
              id="usernameInput"
              className={styles.input}
              placeholder="user12"
              onChange={(e) =>
                setUserSingupRequest({
                  ...userSingupRequest,
                  userName: e.target.value,
                })
              }
            />
          </div>
          <div className={styles.formField}>
            <label htmlFor="passwordInput" className={styles.title}>
              Password
            </label>
            <input
              value={userSingupRequest.password}
              type="password"
              id="passwordInput"
              className={styles.input}
              placeholder="%der351"
              onChange={(e) => {
                setUserSingupRequest({
                  ...userSingupRequest,
                  password: e.target.value,
                });
              }}
            />
          </div>

          <div className={styles.formField}>
            <label htmlFor="emailInput" className={styles.title}>
              E-mail
            </label>
            <input
              value={userSingupRequest.email}
              type="text"
              id="emailInput"
              className={styles.input}
              placeholder="user12@gmail.com"
              onChange={(e) => {
                setUserSingupRequest({
                  ...userSingupRequest,
                  email: e.target.value,
                });
              }}
            />
          </div>

          <button type="submit" className={styles.button}>
            Create account
          </button>
        </form>
      </div>
    </>
  );
}
