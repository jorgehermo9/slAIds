import styles from "./carouselCard.module.scss";

export const ResumeCard = () => {
  return (
    <div className={styles.resumeCard}>
      <label htmlFor="titleInput" className={styles.title}>
        Title
      </label>
      <input
        type="text"
        id="titleInput"
        className={styles.input}
        placeholder="El david la mama"
      />
      <label htmlFor="descriptionTextArea" className={styles.title}>
        Description
      </label>
      <textarea
        id="descriptionTextArea"
        className={styles.input}
        placeholder="El david la mama"
      />
    </div>
  );
};
