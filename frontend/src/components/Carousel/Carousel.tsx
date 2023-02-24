import { Children, useState } from "react";
import styles from "./carousel.module.scss";
import ArrowFwdIcon from "@mui/icons-material/ArrowForwardIosRounded";
import ArrowBckIcon from "@mui/icons-material/ArrowBackIosRounded";

interface Props {
  properties: {
    label: string;
    icon: JSX.Element;
  }[];
  children: JSX.Element | JSX.Element[];
}

export const Carousel = ({ properties, children }: Props) => {
  const childrenArray = Children.toArray(children);
  const [index, setIndex] = useState(0);

  const isFirst = (i: number) => i === 0;
  const isLast = (i: number) => i === childrenArray.length - 1;

  const nextCard = () => {
    if (!isLast(index)) {
      setIndex(index + 1);
    }
  };

  const prevCard = () => {
    if (!isFirst(index)) {
      setIndex(index - 1);
    }
  };

  return (
    <div className={styles.externalContainer}>
      <div className={styles.carouselContainer}>
        <div
          className={styles.arrowCircle}
          data-is-enabled={!isFirst(index)}
          onClick={() => prevCard()}
        >
          <ArrowBckIcon />
        </div>
        <div className={styles.carouselCardContainer}>
          <div
            className={styles.carouselCardWrapper}
            style={{ left: `-${index * 100}%` }}
          >
            {children}
          </div>
        </div>
        <div
          className={styles.arrowCircle}
          data-is-enabled={!isLast(index)}
          onClick={() => nextCard()}
        >
          <ArrowFwdIcon />
        </div>
      </div>
      <div className={styles.carouselDotsContainer}>
        {properties.map(({ label, icon }, i) => (
          <>
            <div
              key={i}
              data-is-active={i === index}
              className={styles.carouselDot}
              onClick={() => setIndex(i)}
            >
              <div className={styles.dotCircle}>{icon}</div>
              <span className={styles.dotLabel}>{label}</span>
            </div>

            {!isLast(i) && <div key={i} className={styles.dotSeparator} />}
          </>
        ))}
      </div>
    </div>
  );
};
