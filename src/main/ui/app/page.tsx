import {buttonVariants } from "@/components/ui/button"


export default function Home() {
  return (
    <div className="relative overflow-hidden py-24 lg:py-32">
      <div className="container mx-auto">
        <div className="max-w-2xl text-center mx-auto">
          <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
            Parte y Reparte: Una nueva forma de comprar
          </h1>
          <p className="mt-3 text-xl text-muted-foreground">
            Comprar productos y servicios colaborativamente nunca fue tan fácil.
          </p>
        </div>
        <div className="mt-10 relative max-w-5xl mx-auto flex justify-center">
          <img
            src="https://plus.unsplash.com/premium_photo-1673977133409-b5c2ff90c9b6?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fHNob3B8ZW58MHwwfDB8fHww"
            className="rounded-xl"
            alt="Image Description"
          />
          <div className="absolute bottom-12 -left-20 -z-[1] w-48 h-48 bg-gradient-to-b from-primary-foreground via-primary-foreground to-background p-px rounded-lg">
            <div className="w-48 h-48 rounded-lg bg-background/10" />
          </div>
          <div className="absolute -top-12 -right-20 -z-[1] w-48 h-48 bg-gradient-to-t from-primary-foreground via-primary-foreground to-background p-px rounded-full">
            <div className="w-48 h-48 rounded-full bg-background/10" />
          </div>
        </div>
      </div>
    </div>
  );

}

//npx shadcn-ui@latest add button
