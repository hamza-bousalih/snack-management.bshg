export const handleInput = (event, setter) => {
  const { name, value } = event.target
  setter((prev) => ({ ... prev, [name]: value }));
}

export const modes = {
  create: 'create',
  delete: 'delete',
  update: 'update',
  non: 'non'
}
