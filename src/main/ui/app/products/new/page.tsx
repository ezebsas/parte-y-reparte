"use client"
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useSession } from "next-auth/react";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useState } from "react";
import { redirect } from 'next/navigation'

const formSchema = z.object({
  name: z.string().min(1, {
    message: "Name is required.",
  }),
  quantity: z.number().int().min(1, {
      message: "Product quantity must be at least 1.",
    }),
  image: z.string().url({
    message: "Image must be a valid URL.",
  }),
  link: z.string().url({
    message: "Link must be a valid URL.",
  }),
  maxPeople: z.number().int().min(1, {
    message: "Maximum people must be at least 1.",
  }),
  minPeople: z.number().int().min(1, {
    message: "Minimum people must be at least 1.",
  }),
  deadline: z.date().refine((value) => {
    return value instanceof Date; 
  }, "Invalid date format. Expected a Date object."),
  totalCost: z.number().min(0, {
    message: "Total cost must be a non-negative number.",
  }),
  subscribers: z.array(z.string()),
  state: z.enum(["OPEN", "CLOSED"]),
});



export default function ProductForm() {
  const { data: session } = useSession();
  const sessionToken = session?.user.value?.token;
  const form = useForm({ resolver: zodResolver(formSchema) });
  const [redir, setRedirect] = useState(false);

  const onSubmit = async () => {
    try {
      const formData = form.getValues();
      const res = await fetch("http://localhost:8080/api/v1/products", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${sessionToken}`,
        },
        body: JSON.stringify(formData),
      });

      if(res.ok){
        setRedirect(true);
      }
  
    
    } catch (error) {
      console.error('Error submitting form:', error.message);
    } 
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    console.log('Submitting form...');
    onSubmit();
  };

  if(redir){
    redirect('/products');
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <Input
                  placeholder="Product's name"
                  value={field.value || ''}
                  onChange={(e) => field.onChange(e.target.value)}
                />

              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="quantity"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Quantity</FormLabel>
              <FormControl>
                <Input
                  type="number"
                  step="0.01"
                  placeholder="10.0"
                  onChange={(e) => {
                    const value = parseFloat(e.target.value);
                    field.onChange(value);
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="unit"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Unit</FormLabel>
              <FormControl>
                <select
                  {...field}
                  defaultValue=""
                  className="block w-full px-4 py-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500"
                >
                  <option value="" disabled>Seleccione una unidad</option>
                  <option value="KILOGRAM">Kilogramo</option>
                  <option value="GRAM">Gramo</option>
                  <option value="LITER">Litro</option>
                  <option value="MILLILITER">Mililitro</option>
                  <option value="UNIT">Unidad</option>
                </select>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="image"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Image URL</FormLabel>
              <FormControl>
                <Input
                  placeholder="https://upload.wikimedia.org/..."
                  value={field.value || ''}
                  onChange={(e) => field.onChange(e.target.value)}
                />

              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="link"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Link URL</FormLabel>
              <FormControl>
                <Input
                  placeholder="https://www.my-product.com"
                  value={field.value || ''}
                  onChange={(e) => field.onChange(e.target.value)}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="maxPeople"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Maximum People</FormLabel>
              <FormControl>
                <Input
                  type="number"
                  inputMode="numeric"
                  placeholder="3"
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    field.onChange(value);
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="minPeople"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Minimum People</FormLabel>
              <FormControl>
                <Input
                  type="number"
                  inputMode="numeric"
                  placeholder="1"
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    field.onChange(value);
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="deadline"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Deadline</FormLabel>
              <FormControl>
                <DatePicker
                  selected={field.value}
                  onChange={(date) => field.onChange(date)}
                  showTimeInput
                  dateFormat="yyyy-MM-dd'T'HH:mm:ss'Z'"
                  placeholderText="Select deadline"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="totalCost"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Total Cost</FormLabel>
              <FormControl>
                <Input
                  type="number"
                  step="0.01"
                  placeholder="30.6"
                  onChange={(e) => {
                    const value = parseFloat(e.target.value);
                    field.onChange(value);
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" onClick={handleFormSubmit}>Submit</Button>
      </form>
    </Form>
  );
}
