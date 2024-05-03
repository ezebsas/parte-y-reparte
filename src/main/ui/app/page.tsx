import { buttonVariants } from "@/components/ui/button"
import Link from 'next/link'


export default function Home() {
  return (
  <div>
    <p>Landing page</p>
    <Link href="products" className={buttonVariants({ variant: "outline" })}>Productos</Link>
  </div>
  );

}

//npx shadcn-ui@latest add button
