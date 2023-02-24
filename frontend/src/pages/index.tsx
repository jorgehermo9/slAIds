import { Carousel } from "@/components/Carousel/Carousel";

export default function Home() {
  return (
    <Carousel labels={["First", "Second"]}>
      <div>
        <h1>Holaa</h1>
      </div>
      <div>
        <h2>adioss</h2>
      </div>
    </Carousel>
  );
}
