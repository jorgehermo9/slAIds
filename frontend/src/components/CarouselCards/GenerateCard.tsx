import PresentationRequest from "@/entities/PresentationRequest";
import styles from "./carouselCard.module.scss";
import SendRoundedIcon from "@mui/icons-material/SendRounded";
import PresentationFile from "@/entities/PresentationFile";
import { NotificationContext } from "../NotificationManager/NotificationManager";
import { useContext, useState } from "react";
import PresentationService from "@/services/PresentationService";
import Presentation from "@/entities/Presentation";
import CircularProgress from "@mui/material/CircularProgress";
import { AnimatePresence, motion } from "framer-motion";

interface Props {
  presentationRequest: PresentationRequest;
  setPresentationRequest: (presentationRequest: PresentationRequest) => void;
  setPresentationFile: (presentationFile: PresentationFile) => void;
  setIsPreviewOpen: (isPreviewOpen: boolean) => void;
}

export const GenerateCard = ({
  presentationRequest,
  setPresentationRequest,
  setIsPreviewOpen,
  setPresentationFile,
}: Props) => {
  const { createSuccessNotification, createErrorNotification } =
    useContext(NotificationContext)!;
  const [isGenerating, setIsGenerating] = useState(false);

  function waitForPresentation(id: Presentation["id"]) {
    PresentationService.isAvailable(id)
      .then((isAvailable) => {
        if (!isAvailable) {
          setTimeout(() => waitForPresentation(id), 5000);
          return;
        }
        createSuccessNotification("Presentations generated successfully", 5000);
        setIsGenerating(false);
        PresentationService.getPresentationFile(id).then((presentationFile) => {
          setPresentationFile(presentationFile);
          setIsPreviewOpen(true);
        });
      })
      .catch(() => {
        setIsGenerating(false);
        createErrorNotification("Error while generating presentations", 5000);
      });
  }

  const handleClick = () => {
    if (isGenerating) return;
    setIsGenerating(true);

    PresentationService.generatePresentation(presentationRequest)
      .then((id) => waitForPresentation(id))
      .catch(() => {
        setIsGenerating(false);
        createErrorNotification("Error while generating presentation", 5000);
      });
  };

  return (
    <div className={styles.cardContainer}>
      <button className={styles.generateButton} onClick={handleClick}>
        <span>Generate</span>
        <AnimatePresence>
          {isGenerating ? (
            <motion.div
              transition={{ duration: 0.3 }}
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
            >
              <CircularProgress className={styles.spinner} />
            </motion.div>
          ) : (
            <motion.div
              transition={{ duration: 0.3 }}
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
            >
              <SendRoundedIcon className={styles.icon} />
            </motion.div>
          )}
        </AnimatePresence>
      </button>
    </div>
  );
};
