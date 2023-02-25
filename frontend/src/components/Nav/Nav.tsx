import React from "react";
import AccountCircleRoundedIcon from "@mui/icons-material/AccountCircleRounded";
import styles from "./nav.module.scss";
import Link from "next/link";

export const Nav = () => {
  return (
    <nav className={styles.navbar}>
      <div>
        <AccountCircleRoundedIcon className={styles.user} />
      </div>
      <div className={styles.buttonsWrapper}>
        <button className={styles.buttonLogin}>Login</button>
        <button className={styles.buttonSignup}>Sign Up</button>
      </div>
    </nav>
  );
};
