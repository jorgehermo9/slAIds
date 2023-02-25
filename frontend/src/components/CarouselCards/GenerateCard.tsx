import SlideRequest from "@/entities/SlideRequest";
import styles from "./carouselCard.module.scss";
import SendRoundedIcon from "@mui/icons-material/SendRounded";
import PresentationFile from "@/entities/PresentationFile";
import { NotificationContext } from "../NotificationManager/NotificationManager";
import { useContext, useState } from "react";
import SlideService from "@/services/SlideService";
import Slide from "@/entities/Slide";

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
  const [isGenerating, setIsGenerating] = useState(false);

  function waitForPresentation(id: Slide["id"]) {
    SlideService.isAvailable(id)
      .then((isAvailable) => {
        if (!isAvailable) {
          setTimeout(() => waitForPresentation(id), 2000);
          return;
        }
        createSuccessNotification("Slides generated successfully", 5000);
        setIsGenerating(false);
        SlideService.getPresentationFile(id).then((presentationFile) => {
          setPresentationFile(presentationFile);
          setIsPreviewOpen(true);
        });
      })
      .catch(() => {
        setIsGenerating(false);
        createErrorNotification("Error while generating slides", 5000);
      });
  }

  const handleClick = () => {
    if (isGenerating) return;
    setIsGenerating(true);

    SlideService.generatePresentation(slideRequest)
      .then((id) => waitForPresentation(id))
      .catch(() =>
        createErrorNotification("Error while generating presentation", 5000)
      );
  };

  return (
    <div className={styles.cardContainer}>
      <button className={styles.generateButton} onClick={handleClick}>
        Generate
        <SendRoundedIcon className={styles.icon} />
      </button>
    </div>
  );
};
