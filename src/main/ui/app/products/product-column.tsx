"use client"
 
import { ProductCard } from "@/components/ui/ProductCard";
import { IProduct } from "@/interfaces/parte-y-reparte-interfaces";
import { ColumnDef } from "@tanstack/react-table"

export const productColumn: ColumnDef<IProduct>[] = [
  {
    accessorKey: "name",
    cell: ({ row }) => {

      const product = row.original;

      return <ProductCard 
          key={product.id} 
          product={product}
          handleClic={() => {
            window.location.href = `/products/${product.id}`
          }}
          />
    }
  },
]