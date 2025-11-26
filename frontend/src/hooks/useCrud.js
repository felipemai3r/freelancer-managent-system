import { useState, useCallback } from "react";

export default function useCrud(service) {
  const [items, setItems] = useState([]); // ✅ MUDOU: [] em vez de undefined
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchAll = useCallback(async (params) => {
    setLoading(true);
    setError(null);
    try {
      const res = await service.listar(); // ✅ MUDOU: list() → listar()
      setItems(res.data || []); // ✅ MUDOU: fallback para []
      return res.data;
    } catch (err) {
      setError(err);
      setItems([]); // ✅ ADICIONADO: seta [] em caso de erro
      throw err;
    } finally {
      setLoading(false);
    }
  }, [service]);

  const create = useCallback(async (payload) => {
    const res = await service.criar(payload); // ✅ MUDOU: create() → criar()
    setItems((s) => [res.data, ...s]);
    return res.data;
  }, [service]);

  const update = useCallback(async (id, payload) => {
    const res = await service.update(id, payload);
    setItems((s) => s.map(i => (i.id === res.data.id ? res.data : i)));
    return res.data;
  }, [service]);

  const remove = useCallback(async (id) => {
    await service.remove(id);
    setItems((s) => s.filter(i => i.id !== id));
  }, [service]);

  return { items, setItems, loading, error, fetchAll, create, update, remove };
}
