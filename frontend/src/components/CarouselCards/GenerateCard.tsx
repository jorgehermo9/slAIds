import SlideRequest from "@/entities/SlideRequest";
import styles from "./generateCard.module.scss";
import SendRoundedIcon from "@mui/icons-material/SendRounded";
import PresentationFile from "@/entities/PresentationFile";
import { NotificationContext } from "../NotificationManager/NotificationManager";
import { useContext } from "react";

interface Props {
  slideRequest: SlideRequest;
  setSlideRequest: (slideRequest: SlideRequest) => void;
  setPresentationFile: (presentationFile: PresentationFile) => void;
  setIsPreviewOpen: (isPreviewOpen: boolean) => void;
}

export const GenerateCard = ({
  slideRequest,
  setSlideRequest,
  setIsPreviewOpen,
}: Props) => {
  const { createSuccessNotification, createErrorNotification } =
    useContext(NotificationContext)!;

  return (
    <div className={styles.resumeCard}>
      <button
        className={styles.generateButton}
        onClick={() => {
          createSuccessNotification("Presentation generated!");
          setIsPreviewOpen(true);
        }}
      >
        Generate
        <SendRoundedIcon className={styles.icon} />
      </button>
    </div>
  );
};
