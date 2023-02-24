import SlideRequest from "@/entities/SlideRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const ResumeCard = ({ slideRequest, setSlideRequest }: Props) => {
  return (
    <div className={styles.resumeCard}>
      <label htmlFor="titleInput" className={styles.title}>
        Title
      </label>
      <input
        value={slideRequest.title}
        type="text"
        id="titleInput"
        className={`${styles.titleInput} ${styles.marginBottom}`}
        placeholder="FIC slides for 2023"
        onChange={(e) => {
          setSlideRequest({ ...slideRequest, title: e.target.value });
        }}
      />
      <label htmlFor="descriptionTextArea" className={styles.title}>
        Description
      </label>
      <textarea
        value={slideRequest.description}
        id="descriptionTextArea"
        className={styles.textArea}
        placeholder="This slides are for the FIC database department, they will be used to teach the new interns how to use sql."
        onChange={(e) => {
          setSlideRequest({ ...slideRequest, description: e.target.value });
        }}
      />
    </div>
  );
};
