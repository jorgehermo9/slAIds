import PresentationRequest from "@/entities/PresentationRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  presentationRequest: PresentationRequest;
  setPresentationRequest: (presentationRequest: PresentationRequest) => void;
}

export const OptionsCard = ({
  presentationRequest,
  setPresentationRequest,
}: Props) => {
  return (
    <div className={styles.cardContainer}>
      <div className={styles.formField}>
        <label htmlFor="numPresentations" className={styles.inputLabel}>
          Number of slides
        </label>
        <input
          value={presentationRequest.numSlides}
          type="number"
          min="1"
          id="numSlides"
          className={styles.input}
          onChange={(e) => {
            setPresentationRequest({
              ...presentationRequest,
              numSlides: parseInt(e.target.value),
            });
          }}
        />
      </div>

      <div className={styles.wordsContainer}>
        <div className={styles.formField}>
          <label htmlFor="minWords" className={styles.inputLabel}>
            Minimum words
          </label>
          <input
            value={presentationRequest.minWords}
            type="number"
            min="1"
            id="minWords"
            className={styles.input}
            onChange={(e) => {
              setPresentationRequest({
                ...presentationRequest,
                minWords: parseInt(e.target.value),
              });
            }}
          />
        </div>
        <div className={styles.formField}>
          <label htmlFor="maxWords" className={styles.inputLabel}>
            Maximum words
          </label>
          <input
            value={presentationRequest.maxWords}
            type="number"
            min="1"
            id="maxWords"
            className={styles.input}
            onChange={(e) => {
              setPresentationRequest({
                ...presentationRequest,
                maxWords: parseInt(e.target.value),
              });
            }}
          />
        </div>
      </div>

      <div className={styles.checkboxWrapper}>
        <div className={styles.checkboxContainer}>
          <label htmlFor="bulletPoints" className={styles.inputLabel}>
            Bullet points
          </label>
          <div className={styles.dottedLine} />
          <input
            type="checkbox"
            id="bulletPoints"
            className={styles.checkbox}
            onChange={(e) => {
              setPresentationRequest({
                ...presentationRequest,
                bulletPoints: e.target.checked,
              });
            }}
          />
        </div>
      </div>
    </div>
  );
};
