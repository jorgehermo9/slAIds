import styles from "./login.module.scss";

export const Login = () => {
  return (
    <div className={styles.card}>
      <div className={styles.formField}>
        <label htmlFor="usernameInput" className={styles.title}>
          Username
        </label>
        <input
          type="text"
          id="usernameInput"
          className={styles.input}
          placeholder="user12"
        />
      </div>
      <div className={styles.formField}>
        <label htmlFor="passwordInput" className={styles.title}>
          Password
        </label>
        <input
          type="password"
          id="passwordInput"
          className={styles.input}
          placeholder="%der351"
        />
      </div>
      <div className={styles.formField}>
        <label htmlFor="emailInput" className={styles.title}>
          E-mail
        </label>
        <input
          type="text"
          id="emailInput"
          className={styles.input}
          placeholder="user12@gmail.com"
        />
      </div>
    </div>
  );
};
