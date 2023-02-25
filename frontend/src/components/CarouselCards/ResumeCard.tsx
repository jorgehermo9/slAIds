import PresentationRequest from "@/entities/PresentationRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  presentationRequest: PresentationRequest;
  setPresentationRequest: (presentationRequest: PresentationRequest) => void;
}

export const ResumeCard = ({
  presentationRequest,
  setPresentationRequest,
}: Props) => {
  return (
    <div className={styles.cardContainer}>
      <div className={styles.formField}>
        <label htmlFor="titleInput" className={styles.inputLabel}>
          Title
        </label>
        <input
          value={presentationRequest.title}
          type="text"
          id="titleInput"
          className={styles.input}
          placeholder="FIC slides for 2023"
          onChange={(e) => {
            setPresentationRequest({
              ...presentationRequest,
              title: e.target.value,
            });
          }}
        />
      </div>

      <div className={`${styles.formField} ${styles.textAreaContainer}`}>
        <label htmlFor="descriptionTextArea" className={styles.inputLabel}>
          Description
        </label>

        <textarea
          value={presentationRequest.description}
          id="descriptionTextArea"
          className={styles.textArea}
          placeholder="This presentations are for the FIC database department, they will be used to teach the new interns how to use sql."
          onChange={(e) => {
            setPresentationRequest({
              ...presentationRequest,
              description: e.target.value,
            });
          }}
        />
      </div>
    </div>
  );
};
