import SlideRequest from "@/entities/SlideRequest";
import styles from "./generateCard.module.scss";
import SendRoundedIcon from "@mui/icons-material/SendRounded";
import PresentationFile from "@/entities/PresentationFile";
import { NotificationContext } from "../NotificationManager/NotificationManager";
import { useContext } from "react";
import SlideService from "@/services/SlideService";

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
  setPresentationFile,
}: Props) => {
  const { createSuccessNotification, createErrorNotification } =
    useContext(NotificationContext)!;

  return (
    <div className={styles.resumeCard}>
      <button
        className={styles.generateButton}
        onClick={() => {
          SlideService.generatePresentation(slideRequest)
            .then(() => {
              createSuccessNotification("Presentation generated!");
            })
            .catch(() =>
              createErrorNotification(
                "Error while generating presentation",
                5000
              )
            );
          // setIsPreviewOpen(true);
        }}
      >
        Generate
        <SendRoundedIcon className={styles.icon} />
      </button>
    </div>
  );
};
