import { z } from "zod";

export const registerUserPhotoSchema = z.object({
    arquivo: z.object({
        caminho: z.string().nullable(),
        tipoArquivo: z.string().nullable(),
        mimeType: z.string().nullable(),
        tamanhoEmBytes: z.string().nullable(),
        bucketArquivo: z.object({
            nome: z.string().nullable()
        })
    }).optional(),
})

export type RegisterUserPhotoFormData = z.infer<typeof registerUserPhotoSchema>;