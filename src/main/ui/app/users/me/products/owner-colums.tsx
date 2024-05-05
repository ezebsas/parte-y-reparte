"use client"
 
import { Button } from "@/components/ui/button";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ColumnDef } from "@tanstack/react-table"
 
// This type is used to define the shape of our data.
// You can use a Zod schema here if you want.

export type Product = {
  id: string;
  name: string;
  image: string;
  link: string;
  deadline: string;
  maxPeople: number;
  minPeople: number;
  totalCost: number;
  state: ProductState;
  subscribers: User[];
  owner: User;
}

type User = {
  name: string;
  age: number;
  email: string;
  id: number;
}

enum ProductState {
  ACTIVE = "ACTIVE",
  PLANNING = "PLANNING",
  COMPLETED = "COMPLETED",
}

export const ownercolumns: ColumnDef<Product>[] = [
  {
    accessorKey: "name",
    header: "Name",
  },
  {
    accessorKey: "totalCost",
    header: "Total",
    cell: ({ row }) => {
      const amount = parseFloat(row.getValue("totalCost"))
      const formatted = new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "ARS",
      }).format(amount)
 
      return <div className="text-left font-medium">{formatted}</div>
    }
  },
  {
    accessorKey: "deadline",
    header: "Dead Line",
    cell: ({ row }) => {
      const date = new Date(row.getValue("deadline"))
      const formatted = date.toLocaleDateString()
      return <div className="text-left font-medium">{formatted}</div>
    }
  },
  {
    accessorKey: "state",
    header: "Status",
    cell: ({ row }) => {
      const state : ProductState = row.getValue("state")
      let text = ""
      /**  ACTIVE = "ACTIVE",
  PLANNING = "PLANNING",
  COMPLETED = "COMPLETED",
   */

      switch(state) {
        case ProductState.ACTIVE: 
          text = "🟢 Active"
          break
        case ProductState.COMPLETED: 
          text = "🔴 Completed"
          break
        default: 
          text = "🟠 Planning"
          break
      }
      return <div className="text-left font-medium">{text}</div>
    }
  },
  {
    accessorFn: (row) => `${row.minPeople} / ${row.maxPeople}`,
    header: "People",
  },
  {
    accessorKey: "subscribers",
    header: "Suscribers",
    cell: ({ row }) => {
      const suscriber: Array<User> = row.getValue("subscribers")
      return (
        <div className="text-left font-medium">
        <Popover>
                    <PopoverTrigger asChild>
                      <Button variant="outline">{suscriber.length} 👁‍🗨</Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-96">
                      <Table>
                        <TableHeader>
                          <TableRow>
                            <TableHead>Name</TableHead>
                            <TableHead>Email</TableHead>
                          </TableRow>
                        </TableHeader>

                        <TableBody>
                          {suscriber.map((s) => (
                            <TableRow key={s.id}>
                              <TableCell>{s.name}</TableCell>
                              <TableCell>{s.email}</TableCell>
                            </TableRow>
                          ))}
                        </TableBody>
                      </Table>
                    </PopoverContent>
                  </Popover>
        </div>)
    }
  },



]