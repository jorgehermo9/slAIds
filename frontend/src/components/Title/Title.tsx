import styles from "./title.module.scss";

export const Title = () => {
  return (
    <div className={styles.titleContainer}>
      <h1 className={styles.title}>
        <span className={styles.bold}>sl</span>
        <span className={styles.thin}>AI</span>
        <span className={styles.bold}>ds</span>
      </h1>
      <h2 className={styles.subtitle}>
        The fastest way to build your presentations
      </h2>
    </div>
  );
};
