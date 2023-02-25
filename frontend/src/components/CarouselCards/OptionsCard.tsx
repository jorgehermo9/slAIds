import SlideRequest from "@/entities/SlideRequest";
import styles from "./carouselCard.module.scss";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
}

export const OptionsCard = ({ slideRequest, setSlideRequest }: Props) => {
  return (
    <div className={styles.cardContainer}>
      <div className={styles.formField}>
        <label htmlFor="numSlides" className={styles.inputLabel}>
          Number of slides
        </label>
        <input
          value={slideRequest.numSlides}
          type="number"
          min="1"
          id="numSlides"
          className={styles.input}
          onChange={(e) => {
            setSlideRequest({
              ...slideRequest,
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
            value={slideRequest.minWords}
            type="number"
            min="1"
            id="minWords"
            className={styles.input}
            onChange={(e) => {
              setSlideRequest({
                ...slideRequest,
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
            value={slideRequest.maxWords}
            type="number"
            min="1"
            id="maxWords"
            className={styles.input}
            onChange={(e) => {
              setSlideRequest({
                ...slideRequest,
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
              setSlideRequest({
                ...slideRequest,
                bulletPoints: e.target.checked,
              });
            }}
          />
        </div>
      </div>
    </div>
  );
};
