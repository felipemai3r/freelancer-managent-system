import { useState, useCallback } from 'react';

const useCrud = (service) => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchAll = useCallback(async (params = {}) => {
    if (!service.listar) return [];
    
    try {
      setLoading(true);
      setError(null);
      const response = await service.listar(params);
      const data = response.data || [];
      setItems(data);
      return data;
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Erro ao carregar dados';
      setError(errorMsg);
      console.error('Erro no fetchAll:', err);
      return [];
    } finally {
      setLoading(false);
    }
  }, [service]);

  const create = useCallback(async (payload) => {
    if (!service.criar) throw new Error('Método criar não disponível');
    
    try {
      setLoading(true);
      setError(null);
      const response = await service.criar(payload);
      const newItem = response.data;
      setItems((prev) => [...prev, newItem]);
      return newItem;
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Erro ao criar';
      setError(errorMsg);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [service]);

  const update = useCallback(async (id, payload) => {
    if (!service.editar) throw new Error('Método editar não disponível');
    
    try {
      setLoading(true);
      setError(null);
      const response = await service.editar(id, payload);
      const updatedItem = response.data;
      setItems((prev) =>
        prev.map((item) => (item.id === id ? updatedItem : item))
      );
      return updatedItem;
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Erro ao atualizar';
      setError(errorMsg);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [service]);

  const remove = useCallback(async (id) => {
    if (!service.remover) throw new Error('Método remover não disponível');
    
    try {
      setLoading(true);
      setError(null);
      await service.remover(id);
      setItems((prev) => prev.filter((item) => item.id !== id));
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Erro ao remover';
      setError(errorMsg);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [service]);

  return {
    items,
    setItems,
    loading,
    error,
    fetchAll,
    create,
    update,
    remove,
  };
};

export default useCrud;
